
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.CommentService;
import services.CustomerService;
import services.PrivateMessageService;
import services.RequestOfferService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customer;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private PrivateMessageService	privateMessageService;

	@Autowired
	private RequestOfferService		requestOfferService;

	@Autowired
	private ApplicationService		applicationService;


	// Constructors -----------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// List Category ---------------------------------------------------------

	@RequestMapping(value = "/ownDashboard")
	public ModelAndView ownDashboard() {
		ModelAndView res;

		final Double ratioOffersVsRequest = this.requestOfferService.ratioOffersVsRequest();

		final Collection<Double> averageNumberOfOffersAndRequestPerCustomer = new ArrayList<Double>();
		final Double averageNumberOfOffersPerCustomer = this.requestOfferService.averageNumberOfOffersPerCustomer();
		final Double averageNumberOfRequestPerCustomer = this.requestOfferService.averageNumberOfRequestPerCustomer();

		averageNumberOfOffersAndRequestPerCustomer.add(averageNumberOfOffersPerCustomer);
		averageNumberOfOffersAndRequestPerCustomer.add(averageNumberOfRequestPerCustomer);

		final Collection<Double> findAverageNumberApplicationsPerOfferOrRequest = new ArrayList<Double>();
		final Double findAverageNumberApplicationsPerOffer = this.applicationService.findAverageNumberApplicationsPerOffer();
		final Double findAverageNumberApplicationsPerRequest = this.applicationService.findAverageNumberApplicationsPerRequest();

		findAverageNumberApplicationsPerOfferOrRequest.add(findAverageNumberApplicationsPerOffer);
		findAverageNumberApplicationsPerOfferOrRequest.add(findAverageNumberApplicationsPerRequest);

		final Collection<Customer> findCustomersWithMoreAcceptedApplications = this.customerService.findCustomersWithMoreAcceptedApplications();

		final Collection<Customer> findCustomersWithMoreDeniedApplications = this.customerService.findCustomersWithMoreDeniedApplications();

		final Collection<Double> findAverageNumberCommentsPerActorOfferRequest = new ArrayList<Double>();
		final Double findAverageNumberCommentsPerActor = this.commentService.findAverageNumberCommentsPerActor();
		final Double findAverageNumberCommentsPerOffer = this.commentService.findAverageNumberCommentsPerOffer();
		final Double findAverageNumberCommentsPerRequest = this.commentService.findAverageNumberCommentsPerRequest();

		findAverageNumberCommentsPerActorOfferRequest.add(findAverageNumberCommentsPerActor);
		findAverageNumberCommentsPerActorOfferRequest.add(findAverageNumberCommentsPerOffer);
		findAverageNumberCommentsPerActorOfferRequest.add(findAverageNumberCommentsPerRequest);

		final Collection<Double> averageNumberCommentsPostedByAdministratorAndCustomer = new ArrayList<Double>();
		final Double averageNumberCommentsPostedByAdministrator = this.commentService.averageNumberCommentsPostedByAdministrator();
		final Double averageNumberCommentsPostedByCustomer = this.commentService.averageNumberCommentsPostedByCustomer();

		averageNumberCommentsPostedByAdministratorAndCustomer.add(averageNumberCommentsPostedByAdministrator);
		averageNumberCommentsPostedByAdministratorAndCustomer.add(averageNumberCommentsPostedByCustomer);

		final Collection<Actor> findActorsWhoPostedPlus10PercentOfAverageNumberOfComments = this.actorService.findActorsWhoPostedPlus10PercentOfAverageNumberOfComments();

		final Collection<Actor> findActorsWhoPostedLess10PercentOfAverageNumberOfComments = this.actorService.findActorsWhoPostedLess10PercentOfAverageNumberOfComments();

		final Collection<Double> minAvgMaxNumberMessagesSentPerActor = new ArrayList<Double>();
		final Double minNumberMessagesSentPerActor = this.privateMessageService.minNumberMessagesSentPerActor();
		final Double averageNumberMessagesSentPerActor = this.privateMessageService.averageNumberMessagesSentPerActor();
		final Double maxNumberMessagesSentPerActor = this.privateMessageService.maxNumberMessagesSentPerActor();

		minAvgMaxNumberMessagesSentPerActor.add(minNumberMessagesSentPerActor);
		minAvgMaxNumberMessagesSentPerActor.add(averageNumberMessagesSentPerActor);
		minAvgMaxNumberMessagesSentPerActor.add(maxNumberMessagesSentPerActor);

		final Collection<Double> minAvgMaxNumberMessagesReceivedPerActor = new ArrayList<Double>();
		final Double minNumberMessagesReceivedPerActor = this.privateMessageService.minNumberMessagesReceivedPerActor();
		final Double averageNumberMessagesReceivedPerActor = this.privateMessageService.averageNumberMessagesReceivedPerActor();
		final Double maxNumberMessagesReceivedPerActor = this.privateMessageService.maxNumberMessagesReceivedPerActor();

		minAvgMaxNumberMessagesReceivedPerActor.add(minNumberMessagesReceivedPerActor);
		minAvgMaxNumberMessagesReceivedPerActor.add(averageNumberMessagesReceivedPerActor);
		minAvgMaxNumberMessagesReceivedPerActor.add(maxNumberMessagesReceivedPerActor);

		final Collection<Actor> findActorWithMoreMessagesSent = this.actorService.findActorwithMoreMessagesSent();

		final Collection<Actor> findActorWithMoreMessagesReceived = this.actorService.findActorwithMoreMessagesReceived();

		res = new ModelAndView("administrator/ownDashboard");
		res.addObject("ratioOffersVsRequest", ratioOffersVsRequest);
		res.addObject("averageNumberOfOffersAndRequestPerCustomer", averageNumberOfOffersAndRequestPerCustomer);
		res.addObject("findAverageNumberApplicationsPerOfferOrRequest", findAverageNumberApplicationsPerOfferOrRequest);
		res.addObject("findCustomersWithMoreAcceptedApplications", findCustomersWithMoreAcceptedApplications);
		res.addObject("findCustomersWithMoreDeniedApplications", findCustomersWithMoreDeniedApplications);
		res.addObject("findAverageNumberCommentsPerActorOfferRequest", findAverageNumberCommentsPerActorOfferRequest);
		res.addObject("averageNumberCommentsPostedByAdministratorAndCustomer", averageNumberCommentsPostedByAdministratorAndCustomer);
		res.addObject("findActorsWhoPostedPlus10PercentOfAverageNumberOfComments", findActorsWhoPostedPlus10PercentOfAverageNumberOfComments);
		res.addObject("findActorsWhoPostedLess10PercentOfAverageNumberOfComments", findActorsWhoPostedLess10PercentOfAverageNumberOfComments);
		res.addObject("minAvgMaxNumberMessagesSentPerActor", minAvgMaxNumberMessagesSentPerActor);
		res.addObject("minAvgMaxNumberMessagesReceivedPerActor", minAvgMaxNumberMessagesReceivedPerActor);
		res.addObject("findActorWithMoreMessagesSent", findActorWithMoreMessagesSent);
		res.addObject("findActorWithMoreMessagesReceived", findActorWithMoreMessagesReceived);

		return res;

	}
}
