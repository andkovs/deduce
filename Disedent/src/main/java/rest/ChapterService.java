package rest;

import dao.ChapterDAO;
import dao.RoleDAO;
import dao.TokenDAO;
import dao.TokenGenerator;
import dto.ChapterDTO;
import dto.ErrorMessageDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/chapter")
public class ChapterService {

    private final ChapterDAO dao = new ChapterDAO();

    @GET
    @Path("/{id}")
    public Response getChapterById(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                ChapterDTO chapter = dao.getChapterByIdFromDb(id);
                if (chapter == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить главу.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(chapter).build();
            }
        }
        return Response.status(401).build();
    }

    @POST
    public Response setNewChapter(@HeaderParam("token") String token, ChapterDTO chapter) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                ChapterDTO newChapter = dao.setChapterInDB(chapter);
                if (newChapter == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось сохранить информацию о главе.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(newChapter).build();
            }
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateChapter(@HeaderParam("token") String token, @PathParam("id") int id, ChapterDTO chapter) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.updateChapterInDB(id, chapter);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось изменить информацию о главе.");
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
    public Response deleteChapter(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.deleteChapterFromDB(id);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось удалить главу.");
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
