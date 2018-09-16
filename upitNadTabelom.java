try (Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
     Statement stmt = conn.createStatement()) {
    try (ResultSet rs = stmt.executeQuery("SELECT p.ime, c.ime FROM Osoba p, Grad c WHERE p.grad_id = c.id")) {
        while (rs.next())
            System.out.println(rs.getString(1) + ", " + rs.getString(2));
        }
    }
}