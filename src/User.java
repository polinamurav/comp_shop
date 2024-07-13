import java.io.Serializable;

class User implements Serializable {
    private String username;
    private String password;
    private int role;
    private boolean isBlocked; // поле для отслеживания блокировки

    public User(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isBlocked =  false; // Новый пользователь создается незаблокированным
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    //чтобы вывести содержимое из файла в нормальном виде
    @Override
    public String toString()
    {
        return "\nЛогин: " + username + ", пароль: " + password + ", роль: " + role + ", заблокирован: " + isBlocked;
    }

}