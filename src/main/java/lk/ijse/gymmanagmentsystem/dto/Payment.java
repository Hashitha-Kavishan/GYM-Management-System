package lk.ijse.gymmanagmentsystem.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Payment{
    public String Id;
    public String Date;
    public Double Amount;
    public String MemberId;


}
