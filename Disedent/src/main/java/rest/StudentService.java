package rest;


import dao.*;
import dto.ErrorMessageDTO;
import dto.StudentDTO;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;


@Path("/student")
public class StudentService {

    private final StudentDAO dao = new StudentDAO();

    @GET
    @Path("/{id}")
    public Response getStudentById(@HeaderParam("token") String token,  @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                Integer idByToken = new UserDAO().getUserIdByLToken(token);
                Integer userIdByOrderId = new OrderDAO().getUserIdByOrderIdFromDb(id);
                if(idByToken!=userIdByOrderId){
                    return Response.status(401).build();
                }
                StudentDTO student = dao.getStudentByIdFromDb(id);
                if(student==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить заказ.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(student).build();
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
