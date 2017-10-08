package rest;

import dao.RoleDAO;
import dao.TokenDAO;
import dao.TokenGenerator;
import dao.UserDAO;
import dto.ErrorMessageDTO;
import dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/users")
public class UserService {

    private final UserDAO dao = new UserDAO();

    @GET
    public Response getAllUsers(@HeaderParam("token") String token) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                ArrayList<UserDTO> users = dao.getAllUsers();
                if (users == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить " +
                            "информацию о пользователях.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(users).build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin", "student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                UserDTO user = dao.getUserByIdFromDb(id);
                if (user == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить пользователя.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(user).build();
            }
        }
        return Response.status(401).build();
    }

    @POST
    public Response setNewUser(@HeaderParam("token") String token, UserDTO user) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                UserDTO newUser = dao.setUserInDB(user);
                if (newUser == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось сохранить информацию о пользователе. Возможно такой пользователь уже существует.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(newUser).build();
            }
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@HeaderParam("token") String token, @PathParam("id") int id, UserDTO user) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.updateUserInDB(id, user);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось изменить информацию о пользователе. Возможно такой пользователь уже существует.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.deleteUserFromDB(id);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось удалить пользователя.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/enable/{id}")
    public Response enableUser(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.enableUserInDB(id);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось включить пользователя.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/disable/{id}")
    public Response disableUser(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.disableUserInDB(id);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось выключить пользователя.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
    }

    private boolean roleContain(ArrayList<String> roles, String[] allowRoles) {
        for (String a :
                allowRoles) {
            for (String r :
                    roles) {
                if (a.equals(r)) {
                    return true;
                }
            }
        }
        return false;
    }

}
