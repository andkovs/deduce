package rest;

import dao.*;
import dto.ErrorMessageDTO;
import dto.TopicDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/topic")
public class TopicService {

    private final TopicDAO dao = new TopicDAO();

    @GET
    @Path("/{id}")
    public Response getTopicById(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin", "student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                if(!adminContain(roles)){
                    Integer idByToken = new UserDAO().getUserIdByLToken(token);
                    Integer courseId = new CourseDAO().getCourseIdByTopicIdFromDb(id);
                    Boolean isOrder = new OrderDAO().isUserIdCourseId(idByToken, courseId);
                    if(!isOrder){
                        return Response.status(401).build();
                    }
                }
                TopicDTO topic = dao.getTopicByIdFromDb(id);
                if(topic==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить раздел.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(topic).build();
            }
        }
        return Response.status(401).build();
    }

    @POST
    public Response setNewTopic(@HeaderParam("token") String token, TopicDTO topic) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                TopicDTO newTopic = dao.setTopicInDB(topic);
                if(newTopic==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось сохранить информацию о разделе.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(newTopic).build();
            }
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTopic(@HeaderParam("token") String token, @PathParam("id") int id, TopicDTO topic) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.updateTopicInDB(id, topic);
                if(status==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось изменить информацию о разделе.");
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
    public Response deleteTopic( @HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.deleteTopicFromDB(id);
                if(status==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось удалить раздел.");
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

    private boolean adminContain(ArrayList<String> roles) {
            for (String r :
                    roles) {
                if(r.equals("admin")){
                    return true;
                }
            }
        return false;
    }
}
