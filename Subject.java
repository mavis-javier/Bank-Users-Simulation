public class Subject {
        private String username;
        private SecLevel clearance;

        public Subject(String name, SecLevel clearance) {
                this.username = name;
                this.clearance = clearance;
        }

        public String getUsername() {
                return this.username;
        }
        public SecLevel getClearance() {
                return this.clearance;
        }
}