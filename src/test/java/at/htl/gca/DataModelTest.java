package at.htl.gca;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import javax.sql.DataSource;

import static org.assertj.db.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class DataModelTest {

    @Inject
    DataSource dataSource;

    @Test
    public void test01TableGolfer() {
        Table golfer = new Table(this.dataSource, "golfer");

        assertThat(golfer).column("id").isNumber(true);
        assertThat(golfer).column("age").isNumber(true);
        assertThat(golfer).column("hcp").isNumber(true);
        assertThat(golfer).column("name").isText(true);
    }

    @Test
    public void test02TableHobbyPlayer() {
        Table golfer = new Table(this.dataSource, "hobbyplayer");

        assertThat(golfer).column("ispremiummember").isBoolean(true);
    }

    @Test
    public void test03TableTeamPlayer() {
        Table golfer = new Table(this.dataSource, "teamplayer");

        assertThat(golfer).column("isregularplayer").isBoolean(true);
        assertThat(golfer).column("joined").isNumber(true);
        assertThat(golfer).column("team_id").isNumber(true);
    }

    @Test
    public void test04TableTeeTime() {
        Table golfer = new Table(this.dataSource, "teetime");

        assertThat(golfer).column("id").isNumber(true);
        assertThat(golfer).column("time").isDateTime(true);
    }

    @Test
    public void test05TableGolferTeeTime() {
        Table golfer = new Table(this.dataSource, "golfer_teetime");

        assertThat(golfer).column("teetimeid").isNumber(true);
        assertThat(golfer).column("golferid").isNumber(true);
    }

}