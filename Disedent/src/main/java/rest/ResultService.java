package rest;

import dao.*;
import dto.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/results")
public class ResultService {

    private final ResultDAO dao = new ResultDAO();

    @GET
    @Path("/group/{id}")
    public Response getAllResultsByGroupId(@HeaderParam("token") String token, @PathParam("id") int id) throws SQLException, ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                ArrayList<ResultForListDTO> results = dao.getAllResultsByGroupIdFromDb(id);
                if (results == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить " +
                            "информацию о результатах тестирования.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(results).build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/user/{id}")
    public Response getAllResultsByUserId(@HeaderParam("token") String token, @PathParam("id") int id) throws SQLException, ParseException {
        String[] allowRoles = {"student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                Integer idByToken = new UserDAO().getUserIdByLToken(token);
                if(idByToken!=id){
                    return Response.status(401).build();
                }
                ArrayList<ResultUserDTO> userResults = dao.getAllResultsByUserId(id);
                if (userResults == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить " +
                            "информацию о результатах тестирования.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(userResults).build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/order/{id}")
    public Response getAllResultsByOrderId(@HeaderParam("token") String token, @PathParam("id") int id) throws SQLException, ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                ArrayList<ResultDTO> results = dao.getAllResultsByOrderIdFromDb(id);
                if (results == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить " +
                            "информацию о результатах тестирования.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(results).build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/{id}")
    public Response getResultById(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin", "student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                ResultDTO result = dao.getResultByIdFromDb(id);
                if (result == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить результаты тестирования.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(result).build();
            }
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateResult(@HeaderParam("token") String token, @PathParam("id") int id, TestViewDTO test) throws ParseException {
        String[] allowRoles = {"student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.updateResultInDB(id, test);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось изменить информацию о курсе. Возможно курс с таким названием уже существует.");
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
    public Response deleteResult(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.deleteResultFromDB(id);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось удалить результат.");
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

