package lesson44;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SampleDataModel {
    private List<User> customers = new ArrayList<>();

    public SampleDataModel() {
        customers.add(new User("Marco"));
        customers.add(new User("Winston", "Duarte"));
        customers.add(new User("Amos", "Burton", "'Timmy'"));
        customers.get(1).setEmailConfirmed(true);
    }

    public static class User {
        private String firstName;
        private String lastName;
        private String middleName = null;
        private boolean emailConfirmed = false;
        private String email;

        public User(String firstName) {
            this(firstName, null, null);
        }

        public User(String firstName, String lastName) {
            this(firstName, lastName, null);
        }

        public User(String firstName, String lastName, String middleName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.middleName = middleName;
            this.email = firstName+"@test.mail";
        }

        public void setEmailConfirmed(boolean emailConfirmed) {
            this.emailConfirmed = emailConfirmed;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("User{");
            sb.append("firstName='").append(firstName).append('\'');
            sb.append(", lastName='").append(lastName).append('\'');
            sb.append(", middleName='").append(middleName).append('\'');
            sb.append(", emailConfirmed=").append(emailConfirmed);
            sb.append(", email='").append(email).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
