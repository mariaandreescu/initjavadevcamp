package com.adobe.devcamp.controller;

import com.adobe.devcamp.model.Advertiser;
import com.adobe.devcamp.model.Campaign;
import com.adobe.devcamp.model.Publisher;
import com.adobe.devcamp.model.User;
import com.adobe.devcamp.service.GenericService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdvertiserController {

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

    // get all active campaigns
    @GetMapping(path="/campaigns", produces = MediaType.APPLICATION_JSON_VALUE, params = "advertiserId")
    public List<Campaign> getAllActiveCampaigns(@RequestParam(name = "advertiserId") Integer advertiserId) {
        List<Campaign> activeCampaigns = new ArrayList<>(campaignService.getAll(Campaign.class)
                .values());

        return activeCampaigns.stream()
                .filter(campaign -> campaign.getAdvertiserId().intValue() == advertiserId.intValue())
                .filter(campaign -> campaign.getEndTime() > System.currentTimeMillis())
                .filter(campaign -> campaign.getStartTime() < System.currentTimeMillis())
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/advertisers/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getTargetedUsers(@RequestParam(name="advertiserId") Integer advertiserId,
                                       @RequestParam(name="campaignId") Integer campaignId) {

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

        return users.stream()
                .filter(user -> user.getProfile().getGender() == campaign.getTarget().getGender())
                // todo filter by age and domains
                .collect(Collectors.toList());
    }

}
