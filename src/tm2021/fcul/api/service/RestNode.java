package tm2021.fcul.api.service;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tm2021.fcul.api.*;
import tm2021.fcul.api.Retransmition;

import java.util.List;
import java.util.Map;

@Path(RestNode.PATH)
public interface RestNode {

	static final String PATH="";

	/**
	 * Pedido para realizar updates de saldo
	 * @param nodeId the nodeid
	 * @param amount amount
	 * @param nodeFrom id node from
	 * @param node Node
	 * @return 200 OK
	 *         403 if incorrect
	 *         409 otherwise
	 */
	@PUT
	@Path("/nodes/{nodeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	int updateAmount(@PathParam("nodeId") String nodeId, @QueryParam("amount") int amount, @QueryParam("nodeFrom") String nodeFrom,Node node);

	/**
	 * Pedido obter saldo de outro node
	 * @return 200 OK
	 *         403 if incorrect
	 *         409 otherwise
	 */
	@GET
	@Path("/nodes/{nodeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	int getAmountFrom();

	/**
	 * @param retr retrans
	 * @return 200 OK
	 *         existing password
	 *         403 if incorrect
	 *         409 otherwise
	 */
	@PUT
	@Path("/retrans")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	int sendRetrans(Retransmition retr);

	/**
	 * Obter transaçoes da rede
	 * @return 200 OK
	 *         403 if incorrect
	 *         409 otherwise
	 */
	@GET
	@Path("/transacoes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Map<String, Node> getAllTransacoes();


}
