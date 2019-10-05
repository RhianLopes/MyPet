package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Follower extends Domain {

    private Pet petFollower;

    private Pet petFollowed;
}
