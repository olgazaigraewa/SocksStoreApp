package me.olgas.socksstoreapp.model;




public enum SocksColor {
    WHITE("белый"),
    BLACK("черный"),
    RED("красный"),
    YELLOW("желтый"),
    BROWN("коричневый"),
    BLUE("синий");

    final String translation;

    SocksColor(String translation) {
        this.translation = translation;
    }
    public String getTranslation() {
        return translation;
    }



}
