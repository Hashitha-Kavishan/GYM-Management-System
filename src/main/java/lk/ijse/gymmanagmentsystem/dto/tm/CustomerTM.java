package lk.ijse.gymmanagmentsystem.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CustomerTM {
    public String id;
    public String name;
    public String address;
    public String age;
    public String gender;
    public String email;
    public String MobileNumber;
    public String joinDate;
    public String membershipType;

}
