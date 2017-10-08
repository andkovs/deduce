package rest;

import dao.*;
import dto.ErrorMessageDTO;
import dto.TestDTO;
import dto.TestViewDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/tests")
public class TestService {

    private final TestDAO dao = new TestDAO();

    @GET
    public Response getAllTests(@HeaderParam("token") String token) throws SQLException, ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                ArrayList<TestDTO> courses = dao.getAllTestsFromDb();
                if(courses==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить " +
                            "информацию о тестах.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(courses).build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/{id}")
    public Response getTestById(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                TestDTO test = dao.getTestByIdFromDb(id);
                if(test==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить курс.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(test).build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/info/{id}")
    public Response getTestInfoById(@HeaderParam("token") String token, @QueryParam("orderId") int orderId, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                Integer idByToken = new UserDAO().getUserIdByLToken(token);
                Integer userIdByOrderId = new OrderDAO().getUserIdByOrderIdFromDb(orderId);
                if(idByToken!=userIdByOrderId){
                    return Response.status(401).build();
                }
                TestDTO test = dao.getTestInfoByIdFromDb(orderId ,id);
                if(test==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить тест.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(test).build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/view/{id}")
    public Response getTestViewById(@HeaderParam("token") String token, @QueryParam("orderId") int orderId, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                TestViewDTO test = dao.getTestViewByIdFromDb(orderId ,id);
                if(test==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить тест.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(test).build();
            }
        }
        return Response.status(401).build();
    }

    @POST
    public Response setNewTest(@HeaderParam("token") String token, TestDTO test) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                TestDTO newTest = dao.setTestInDB(test);
                if(newTest==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось сохранить информацию о тесте. " +
                            "Возможно тест с таким названием уже существует.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(newTest).build();
            }
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTest(@HeaderParam("token") String token, @PathParam("id") int id, TestDTO test) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.updateTestInDB(id, test);
                if(status==null){
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
    public Response deleteTest(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.deleteTestFromDB(id);
                if(status==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось удалить курс.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
    }

    private boolean roleContain(ArrayList<String> roles, String[] allowRoles) {
        for (String a:
                allowRoles) {
            for (String r :
                    roles) {
                if(a.equals(r)){
                    return true;
                }
            }
        }
        return false;
    }
}

