package by.it.academy.blockchain.domain;

public final class UserView {
    public interface RequiredFieldView {} // интерфейс маркер (помеченное им поле сущности будет выгружаться json)

    public interface FullView extends RequiredFieldView {} // все поля выгружаются в json
}
