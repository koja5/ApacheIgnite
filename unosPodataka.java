try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Grad (id, ime) VALUES (?, ?)")) {
    stmt.setLong(1, 1L);
    stmt.setString(2, "Subotica");
    stmt.executeUpdate();
    stmt.setLong(1, 2L);
    stmt.setString(2, "Beograd");
    stmt.executeUpdate();
    stmt.setLong(1, 3L);
    stmt.setString(2, "Kragujevac");
    stmt.executeUpdate();
}
try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Osoba (id, ime, grad_id) VALUES (?, ?, ?)")) {
    stmt.setLong(1, 1L);
    stmt.setString(2, "Marko Markovic");
    stmt.setLong(3, 3L);
    stmt.executeUpdate();
    stmt.setLong(1, 2L);
    stmt.setString(2, "Petar Petrovic");
    stmt.setLong(3, 2L);
    stmt.executeUpdate();
    stmt.setLong(1, 3L);
    stmt.setString(2, "Ana Ivanovic");
    stmt.setLong(3, 1L);
    stmt.executeUpdate();
    stmt.setLong(1, 4L);
    stmt.setString(2, "Jelena Jankovic");
    stmt.setLong(3, 2L);
    stmt.executeUpdate();
}