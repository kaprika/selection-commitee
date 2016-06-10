package by.training.lysiuk.project.dataaccess.filters;


public class UserFilter extends AbstractFilter {

    private String userName;

    private boolean isFetchCredentials;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isFetchCredentials() {
        return isFetchCredentials;
    }

    public void setFetchCredentials(boolean isFetchCredentials) {
        this.isFetchCredentials = isFetchCredentials;
    }

}
