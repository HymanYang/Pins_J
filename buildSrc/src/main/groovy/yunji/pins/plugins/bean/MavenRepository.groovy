package yunji.pins.plugins.bean

class MavenRepository {

    String url
    Object authentication

    void url(String url) {
        this.url = url
    }

    void authentication(Object values) {
        this.authentication = values
    }

}