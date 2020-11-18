package models.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardVisibilityRestrict {

    //  private String private;
    private String org;
    private String enterprise;
    //  private String public;


    //private": "org",
    ////                "org": "org",
    ////                "enterprise": "org",
    ////                "public": "org"
}
