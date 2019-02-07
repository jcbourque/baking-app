package com.example.android.bakingapp.data;

import lombok.Data;

@Data
public class Step {
    /*
          {
        "id": 6,
        "shortDescription": "Add eggs.",
        "description": "6. Scrape down the sides of the pan. Add in the eggs one at a time, beating each one on medium-low speed just until incorporated. Scrape down the sides and bottom of the bowl. Add in both egg yolks and beat until just incorporated. ",
        "videoURL": "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb55_4-add-eggs-mix-cheesecake/4-add-eggs-mix-cheesecake.mp4",
        "thumbnailURL": ""
      },

     */
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
}
