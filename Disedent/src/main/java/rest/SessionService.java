package rest;

import dao.*;
import dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

@Path("/session")
public class SessionService {

    @GET
    public Response getSession(@Context HttpServletRequest req, @HeaderParam("token") String token) throws ParseException {
        if(ContextName.getName()==null){
            String name = req.getContextPath();
            name = name.replace("/", "");
            ContextName.setName(name);
        }
        if (token == null || token == "") {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            SessionDTO session = new SessionDTO();
            session.setRoles(new RoleDAO().getRolesByToken(token));
            String login = new UserDAO().getLoginByTokenFromDB(token);
            session.setUser(new UserDAO().getUserByLogin(login));
            new TokenDAO().updateTokenTimeByTokenInDB(token);
            return Response.ok().entity(session).build();

        }
        return Response.status(401).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginDTO login) {
        UserDTO user = new UserDAO().getUserByLogin(login.getLogin());
        ArrayList<String> roles = new RoleDAO().getRolesByLogin(login.getLogin());
        if (user.getLogin() == null) {
            ErrorMessageDTO err = new ErrorMessageDTO("Неверный логин");
            return Response.status(402).entity(err).build();//неверный логин
        }
        if (login.getPassword().equals(user.getPassword())) {
            user.setPassword(null);
            SessionDTO session = new SessionDTO();
            session.setUser(user);
            new TokenDAO().updateTokenTimeByLoginInDB(login.getLogin());
            TokenDTO token = new TokenDAO().getTokenByLoginFromDB(login.getLogin());
            session.setToken(token.getToken());
            session.setRoles(roles);
            return Response.status(200).entity(session).build();
        } else {
            ErrorMessageDTO err = new ErrorMessageDTO("Неверный пароль");
            return Response.status(402).entity(err).build();//неверный пароль
        }
    }

}
