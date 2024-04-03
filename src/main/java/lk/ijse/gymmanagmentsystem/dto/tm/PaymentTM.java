package lk.ijse.gymmanagmentsystem.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PaymentTM {
    public String Id;
    public String Date;
    public Double Amount;
    public String MemberId;

}