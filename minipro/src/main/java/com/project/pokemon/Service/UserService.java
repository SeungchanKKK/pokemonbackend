package com.project.pokemon.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.project.pokemon.model.dto.requestDto.SignInDto;
import com.project.pokemon.model.dto.requestDto.SignupDto;
import com.project.pokemon.model.dto.responseDto.UserLoginRespDto;
import com.project.pokemon.model.dto.responseDto.UserRegisterRespDto;
import com.project.pokemon.model.dto.responseDto.UserTokenRespDto;
import com.project.pokemon.model.entity.RefreshToken;
import com.project.pokemon.model.entity.Users;
import com.project.pokemon.model.repository.RefreshTokenRepository;
import com.project.pokemon.model.repository.UserRepository;
import com.project.pokemon.security.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    public String generateRefreshToken(Date now, Date expireDate) {
        String token = "";

        try {
            // secret 키 노출되면 안돼서, secret.json 파일로 빼서 gitignore 파일에 secret.json 을 추가하는 식으로 막음
            // 테스트니까 그냥 아무 키 값 사용 / TokenFilter 와 동일한 시크릿 키, 발급자여야 함.
            Algorithm algorithm = Algorithm.HMAC256("rlaalswnrkgoTdma");
            token = JWT.create()
                    .withIssuer("gkdgo99") // 발급자
                    .withIssuedAt(now) // 생성시간
                    .withExpiresAt(expireDate) // 만료기간
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return "";
        }

        return token;
    }

    public UserLoginRespDto generateToken(Long id, String email, String nickname) {
        String token = "";
        Date now = new Date();
        Date expireDate = new Date(System.currentTimeMillis() + (1000L * 3600L * 24L * 7L)); // 7일
        Date refreshExpireDate = new Date(System.currentTimeMillis() + (1000L * 3600L * 24L * 30L)); // 30일

        // 일반적으로 엑세스 토큰보다 리프레시 토큰 수명 주기가 더 길어야 함
        // 엑세스 토큰은 7일, 리프레시 토큰은 30일
        String refreshToken = generateRefreshToken(now, refreshExpireDate);

        if(refreshToken.equals(""))
            return new UserLoginRespDto(false, "리프레시 토큰 생성에 실패 하였습니다.");

        try {
            Algorithm algorithm = Algorithm.HMAC256("rlaalswnrkgoTdma");
            token = JWT.create()
                    .withIssuer("gkdgo99")
                    .withIssuedAt(now)
                    .withExpiresAt(expireDate)
                    .withClaim("id", id)
                    .withClaim("email", email)
                    .withClaim("nickname", nickname)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return new UserLoginRespDto(false, "토큰 생성에 실패 하였습니다.");
        }

        // DB 에 리프레시 토큰을 저장 해야함
        // 나중에 갱신 할 때 DB 에 해당 리프레시 토큰과 엑세스 토큰이 같은지를 비교해서 해당 엑세스 토큰의 리프레시 토큰인지 확인을 해야함
        refreshTokenRepository.save(new RefreshToken(token, refreshToken));

        return new UserLoginRespDto(true, token, refreshToken, "로그인 성공");
    }

    // 토큰 재발급
    public UserLoginRespDto verifyRefreshToken(String accessToken, String refreshToken) {
        DecodedJWT refresh_decode = null;

        // 리프레시 토큰은 만료기한 여부 등 인증 과정을 거침
        try {
            Algorithm algorithm = Algorithm.HMAC256("rlaalswnrkgoTdma"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("gkdgo99")
                    .build(); //Reusable verifier instance

            refresh_decode = verifier.verify(refreshToken);
        } catch (JWTVerificationException exception) {
            return new UserLoginRespDto(false, "리프레시 토큰이 유효하지 않습니다.");
        }

        // 액세스 토큰은 이미 만료 되었기 때문에 위에 방식 말고 decode 함수를 써서 만료와 상관없이 해독하는 함수를 사용
        DecodedJWT access_decode;

        try {
            access_decode = JWT.decode(accessToken);
        } catch (JWTVerificationException exception){
            return new UserLoginRespDto(false, "액세스 토큰이 유효하지 않습니다.");
        }

        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByAccessTokenAndRefreshToken(accessToken, refreshToken);

        if(!refreshTokenOpt.isPresent()) {
            return new UserLoginRespDto(false, "토큰 재발급 실패");
        }

        Long id = Long.parseLong(access_decode.getClaim("id").toString());
        String email = access_decode.getClaim("email").toString();
        String nickname = access_decode.getClaim("nickname").toString();

        return this.generateToken(id, email, nickname);
    }


    // 토큰 테스트
    public UserTokenRespDto tokenTest(String token)
    {
        DecodedJWT jwt;

        try {
            Algorithm algorithm = Algorithm.HMAC256("rlaalswnrkgoTdma"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("gkdgo99")
                    .build(); //Reusable verifier instance

            jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            return new UserTokenRespDto(false, "토큰이 유효하지 않습니다.");
        }

        return new UserTokenRespDto(true, jwt.getPayload(), "토큰 해석 성공");
    }


    public UserLoginRespDto loginUser(SignInDto Dto) throws NoSuchAlgorithmException {
        Boolean result = true;
        String errMsg = "로그인 성공";

        String email = Dto.getEmail();
        String pw = Dto.getPw();
        String pwSHA256 = SHA256.encrypt(pw);

        Optional<Users> user = userRepository.findByEmailAndPassword(email, pwSHA256);

        if(!user.isPresent()) {
            errMsg = "아이디 또는 비밀번호가 틀렸습니다.";
            result = false;
            return new UserLoginRespDto(result, errMsg);
        }

        return this.generateToken(user.get().getId(), user.get().getEmail(), user.get().getNickname());
    }

    public UserRegisterRespDto registerUser(SignupDto Dto) throws NoSuchAlgorithmException {
        Boolean result = true;
        String err_msg = "회원가입 성공";
        String email = Dto.getEmail();
        String nickname = Dto.getNickname();

        Optional<Users> foundemail = userRepository.findByEmail(email);
        Optional<Users> foundnickname = userRepository.findByNickname(nickname);

        // 회원 ID 중복 확인
        if (foundemail.isPresent()) {
            err_msg = "중복된 사용자 ID가 존재합니다.";
            result = false;
            return new UserRegisterRespDto(result, err_msg);
        }

        // 회원 닉네임 중복 확인
        if (foundnickname.isPresent()) {
            err_msg = "중복된 닉네임이 존재합니다.";
            result = false;
            return new UserRegisterRespDto(result, err_msg);
        }

        // 패스워드 암호화
        String password = SHA256.encrypt(Dto.getPw());

        Users user = new Users(email, nickname, password);
        System.out.println(Dto.getEmail());
        System.out.println(Dto.getNickname());
        userRepository.save(user);

        UserRegisterRespDto responseDto = new UserRegisterRespDto(result, err_msg);
        return responseDto;
    }



    // Email 중복 확인
    public Boolean checkEmailDuplicate(String email) {
        Users user = userRepository.findByEmail(email).orElse(null);

        try {
            if (user.getEmail().equals(email)) {
                return false;
            }
        } catch (NullPointerException e) {
            return true;
        }
        return true;
    }

    // 닉네임 중복 확인
    public Boolean checkNameDuplicate(String nickname) {
        Users user = userRepository.findByNickname(nickname).orElse(null);

        try {
            if (user.getNickname().equals(nickname)) {
                return false;
            }
        } catch (NullPointerException e) {
            return true;
        }
        return true;
    }

}
