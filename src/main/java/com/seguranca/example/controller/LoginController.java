package com.seguranca.example.controller;

import com.seguranca.example.JWTUtil;
import com.seguranca.example.domain.Credentials;
import com.seguranca.example.domain.UserLogged;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginController {

    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    @GET
    @Path("/me")
    public UserLogged me(@Context HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(JWTUtil.TOKEN_HEADER);
        Jws<Claims> jws = JWTUtil.decode(token);
        UserLogged me = new UserLogged();
        me.setUsername(jws.getBody().getSubject());
        return me;
    }

    @POST
    @Path("/login")
    public Response login(Credentials credentials) {
        if(this.USERNAME.equals(credentials.getUsername()) && this.PASSWORD.equals(credentials.getPassword())){
            String token = JWTUtil.create(credentials.getUsername());
            UserLogged me = new UserLogged();
            me.setUsername(credentials.getUsername());
            me.setToken(token);
            return Response.ok().entity(me).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
