package tm2021.fcul.api.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import tm2021.fcul.api.*;

@Path(RestNode.PATH)
public interface RestNode {

	static final String PATH="/nodes";


	
	/**
	 * Modifies the information of a user. Values of null in any field of the user will be 
	 * considered as if the the fields is not to be modified (the name cannot be modified).
	 * @param userId the name of the user
	 * @param amount password of the user
	 * @param node Updated information
	 * @return 200 the updated user object, if the name exists and password matches the
	 *         existing password 
	 *         403 if the password is incorrect or the user does not exist 
	 *         409 otherwise
	 */
	@PUT
	@Path("/{nodeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	int updateAmount(@PathParam("nodeId") String userId, @QueryParam("amount") int amount, Node node);


	/**
	 * Modifies the information of a user. Values of null in any field of the user will be
	 * considered as if the the fields is not to be modified (the name cannot be modified).
	 * @return 200 retrans
	 *         409 otherwise
	 */
	@PUT
	@Path("/retrans/{idTrans}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	int informNodes(@PathParam("idTrans") String idTrans, Retransmition retran);



}
