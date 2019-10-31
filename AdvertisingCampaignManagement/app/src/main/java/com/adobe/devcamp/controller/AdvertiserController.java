package com.adobe.devcamp.controller;

import com.adobe.devcamp.model.*;
import com.adobe.devcamp.service.GenericService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdvertiserController {
    //get all active campaigns
    private final GenericService<Campaign> campaignService;
    private final GenericService<Advertiser> advertiserService;
    private final GenericService<Publisher> publisherService;
    private final GenericService<User> userService;

    public AdvertiserController(GenericService<Campaign> campaignService, GenericService<Advertiser> advertiserService, GenericService<Publisher> publisherService, GenericService<User> userService) {
        this.campaignService = campaignService;
        this.advertiserService = advertiserService;
        this.publisherService = publisherService;
        this.userService = userService;
    }

    @GetMapping(path = "/campaigns", produces = MediaType.APPLICATION_JSON_VALUE, params = "advertiserId")
    public List<Campaign> getAllActiveCampaigns(@RequestParam(name = "advertiserId") Integer advertiserId) {
        List<Campaign> activeCampaigns = new ArrayList<Campaign>(campaignService.getAll(Campaign.class)
                .values());

        return activeCampaigns.stream()
                .filter(campaign -> campaign.getAdvertiserId().intValue() == advertiserId.intValue())
                .filter(campaign -> campaign.getEndTime() > System.currentTimeMillis())
                .filter(campaign -> campaign.getStartTime() < System.currentTimeMillis())
                .collect(Collectors.toList());
    }

    //:: -> method refferences -> tipuri speciale de lambda expressions deja definite; operator
    @GetMapping(path = "/advertisers/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getTargetedUsers(@RequestParam(name = "advertiserId") Integer advertiserId,
                                       @RequestParam(name = "campaignId") Integer campaignId) {
        final Campaign campaign = campaignService.getById(Campaign.class, campaignId);
        final Advertiser advertiser = advertiserService.getById(Advertiser.class, advertiserId);

        final List<Publisher> publishers = advertiser.getPublishers().stream()
                .map(publisherId -> publisherService.getById(Publisher.class, publisherId))
                .collect(Collectors.toList());
        return publishers.stream()
                .map(publisher -> getTargetedUsersFor(publisher, campaign))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<User> getTargetedUsersFor(Publisher publisher, Campaign campaign) {
        final List<User> users = publisher.getUsers().stream()
                .map(userId -> userService.getById(User.class, userId))
                .collect(Collectors.toList());

        return  users.stream()
                //filter by gender
                .filter(user -> user.getProfile().getGender() == campaign.getTarget().getGender())
                //todo filter by age and domains
                //filter by age
                .filter(user ->  (java.time.LocalDate.now().getYear() - user.getProfile().getDateOfBirth().getYear())
                        < campaign.getTarget().getMaxAge())
                .filter(user -> (java.time.LocalDate.now().getYear() - user.getProfile().getDateOfBirth().getYear())
                        > campaign.getTarget().getMinAge())
                //filter by interests
                .filter (user -> checkIfUserIsInterested(user, campaign))
                .collect(Collectors.toList());
    }

    private boolean checkIfUserIsInterested(User user, Campaign campaign) {
        List<Domain> userInterests = user.getProfile().getInterests();
        List<Domain> targetInterests = campaign.getTarget().getInterests();
        for (Domain intUser : userInterests) {
            for (Domain intTarget: targetInterests) {
                if (intUser == intTarget) {
                    return true;
                }
            }
        }
        return false;
    }
}
