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
public class EntryTest {

    @Inject
    DataSource dataSource;

    /**
     * checks if names are persisted correctly and if inheritance works
     */
    @Test
    public void test01GolferData(){
        Table golfer = new Table(this.dataSource, "golfer");
        Table hobbyplayer = new Table(this.dataSource, "hobbyplayer");
        Table teamplayer = new Table(this.dataSource, "teamplayer");

        assertThat(golfer).hasNumberOfRows(6);
        assertThat(teamplayer).hasNumberOfRows(5);
        assertThat(hobbyplayer).hasNumberOfRows(1);

        assertThat(golfer).column("name")
                .hasValues("Leon Kuchinka",
                        "Christoph Bleier",
                        "Philip Pfeifenberger",
                        "Michael Fehringer",
                        "Alex Binder",
                        "Julian Nobis");

    }

    /**
     * checks if number of rows and teamname are correct
     */
    @Test
    public void test02TeamData(){
        Table team = new Table(this.dataSource, "team");

        assertThat(team).hasNumberOfRows(1);
        assertThat(team).row(0)
                .value("teamname").isEqualTo("Mens Team");
    }

    /**
     * checks if both teetimes are persisted and makes sure the time-column does not have null values
     */
    @Test
    public void test03TeeTimeData(){
        Table teetime = new Table(this.dataSource, "teetime");

        assertThat(teetime).hasNumberOfRows(2);
        assertThat(teetime).column("time")
                .hasOnlyNotNullValues();
    }


}
