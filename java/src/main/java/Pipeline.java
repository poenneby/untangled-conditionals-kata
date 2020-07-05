import dependencies.Config;
import dependencies.Emailer;
import dependencies.Logger;
import dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        boolean testsPassed = false;
        if (project.hasTests()) {
            testsPassed = runTests(project);
        } else {
            log.info("No tests");
            testsPassed = true;
        }

        boolean deploySuccessful = false;
        if (testsPassed) {
            deploySuccessful = deploy(project);
        }

        if (config.sendEmailSummary()) {
            sendEmail(testsPassed, deploySuccessful);
        } else {
            log.info("Email disabled");
        }
    }

    private boolean runTests(Project project) {
        if ("success".equals(project.runTests())) {
            log.info("Tests passed");
            return true;
        } else {
            log.error("Tests failed");
            return false;
        }
    }

    private boolean deploy(Project project) {
        if ("success".equals(project.deploy())) {
            log.info("Deployment successful");
            return true;
        }
        log.error("Deployment failed");
        return false;
    }

    private void sendEmail(boolean testsPassed, boolean deploySuccessful) {
        log.info("Sending email");
        if (testsPassed && deploySuccessful) {
            emailer.send("Deployment completed successfully");
            return;
        }
        if (!testsPassed) {
            emailer.send("Tests failed");
            return;
        }
        if (!deploySuccessful) {
            emailer.send("Deployment failed");
            return;
        }
    }




}
