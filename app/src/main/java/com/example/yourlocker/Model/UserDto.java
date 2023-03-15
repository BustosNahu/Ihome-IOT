package com.example.yourlocker.Model;

public class UserDto {
    public String nameUser, emailUser, passUser, addressUser, numberAdressUser, espacioDispositivos, profileUrl;
    private UserDto() {}
    public UserDto(String nameUser, String emailUser, String passUser, String addressUser, String numberAdressUser, String espacioDispositivos, String profileUrl) {
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.passUser = passUser;
        this.addressUser = addressUser;
        this.numberAdressUser = numberAdressUser;
        this.espacioDispositivos = espacioDispositivos;
        this.profileUrl = profileUrl;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        this.passUser = passUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getNumberAdressUser() {
        return numberAdressUser;
    }

    public void setNumberAdressUser(String numberAdressUser) {
        this.numberAdressUser = numberAdressUser;
    }

    public String getEspacioDispositivos() {
        return espacioDispositivos;
    }

    public void setEspacioDispositivos(String espacioDispositivos) {
        this.espacioDispositivos = espacioDispositivos;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
