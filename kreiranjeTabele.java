try (Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
     Statement stmt = conn.createStatement();) {
    //kreiranje tabele za grad
    stmt.executeUpdate("CREATE TABLE Grad (id LONG PRIMARY KEY, ime VARCHAR) WITH \"template=replicated\"");
    //krairanje tabele za osobu
    stmt.executeUpdate("CREATE TABLE Osoba (id LONG, ime VARCHAR, grad_id LONG, PRIMARY KEY (id, grad_id)) WITH \"backups=1, affinityKey=grad_id\"");
    stmt.executeUpdate("CREATE INDEX idx_grad_ime ON Grad (ime)");
    stmt.executeUpdate("CREATE INDEX idx_osoba_ime ON Osoba (ime)");
}