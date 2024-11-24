package co.edu.usco.TM.dto.shared;

public enum PetSpecie {

    DOG("pet.species.icon.DOG", "pet.species.DOG"),
    CAT("pet.species.icon.CAT", "pet.species.CAT"),
    RODENT("pet.species.icon.RODENT", "pet.species.RODENT"),
    FISH("pet.species.icon.FISH", "pet.species.FISH");

    private final String iconKey;
    private final String messageKey;

    // Constructor
    PetSpecie(String iconKey, String messageKey) {
        this.iconKey = iconKey;
        this.messageKey = messageKey;
    }

    public String getIconKey() {
        return iconKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
