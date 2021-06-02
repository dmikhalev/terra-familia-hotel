package cs.vsu.hotel.dto;

import cs.vsu.hotel.entity.Client;
import lombok.Data;

@Data
public class ClientDto {

    private Long id;
    private String name;
    private String passportId;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String email;

    public ClientDto(Long id, String name, String passportId, Integer age, String gender, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.passportId = passportId;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender.equalsIgnoreCase("male") ? "Муж." : "Жен.";
    }

    public ClientDto() {

    }

    public static ClientDto toClientDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getFirstName() + " " + client.getLastName(),
                String.valueOf(client.getPassportId()),
                client.getAge(),
                client.getGender(),
                client.getPhoneNumber(),
                client.getEmail()
        );
    }

    public Long getPassportIdLong() {
        try {
            return Long.parseLong(passportId.replace(" ", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
