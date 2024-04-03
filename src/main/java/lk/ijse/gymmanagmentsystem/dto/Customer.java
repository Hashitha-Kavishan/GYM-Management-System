package lk.ijse.gymmanagmentsystem.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Customer {
    public String id;
    public String name;
    public String address;
    public String age;
    public String email;
    public String MobileNumber;
    public String joinDate;
    public String gender;
    public String membershipType;

}
