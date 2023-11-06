package com.example.employee.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
@Component
public class JwtUtil {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public void validateToken(final String token) {
        System.out.println(token);
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public List<GrantedAuthority> getAuthorities(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        System.out.println(claims.get("roles"));
        String roles = String.valueOf(claims.get("roles"));
        //  System.out.println();
        String[] arr = roles.split(",");
        if(arr.length == 1){
            arr[0] = arr[0].substring(1,arr[0].length()-1);
            // System.out.println(arr[0]+"->"+arr[0].length());
        }else{
            for(int i=0; i<arr.length; i++) {
                System.out.println(arr[i] + "->" + arr[i].length());
                if (i == 0) {
                    arr[i] = arr[i].substring(1);
                } else if (i == arr.length - 1) {
                    arr[i] = arr[i].substring(1, arr[i].length() - 1);
                } else {
                    arr[i] = arr[i].trim();
                }
                System.out.println(arr[i] + "->" + arr[i].length());
            }}

        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (String str : arr){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(str);
            authorityList.add(grantedAuthority);
        }
        return authorityList;
    }
}

