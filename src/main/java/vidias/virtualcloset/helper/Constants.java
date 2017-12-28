package vidias.virtualcloset.helper;

import vidias.virtualcloset.model.BodyPosition;

public class Constants {
    public static final String NON_OPTIONAL_SECTORS_MISSING_MESSAGE = "Existem setores obrigatórios não preenchidos";
    public static final String FORBIDDEN_CLOTHES_OVERLAP_MESSAGE = "Existem sobreposições de roupas não permitidas";
    public static final String DUPPLICATED_MESSAGE = "Item duplicado";
    public static final String OBJECT_NOT_FOUND_MESSAGE = "Objeto não encontrado";
    public static final String SOMETHING_WRONG_RANDOM_MESSAGE = "Não foi possível achar uma combinação por motivos desconhecidos";

    public static String generateMissingClothingForMessage(BodyPosition bodyPosition) {
        String bodyPositionName = "";

        switch (bodyPosition) {
        case BOTTOM:
            bodyPositionName = "a parte de baixo";
            break;
        case FOOT:
            bodyPositionName = "o pé";
            break;
        case HEAD:
            bodyPositionName = "a cabeça";
            break;
        case TOP:
            bodyPositionName = "a parte de cima";
            break;
        default:
            bodyPositionName = "algumas partes do corpo";
            break;
        }

        return String.format("Não existem peças de roupa para %s", bodyPositionName);
    }
}
