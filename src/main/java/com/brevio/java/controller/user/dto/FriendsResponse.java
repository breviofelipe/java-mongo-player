package com.brevio.java.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FriendsResponse {

    private String id, firstName, lastName, occupation, location, picturePath;
}
