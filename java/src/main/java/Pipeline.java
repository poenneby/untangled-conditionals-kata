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
        DeployStep deployStep = new DeployStep(null, log);
        PipelineStep pipelineSteps = new TestsStep(deployStep, log);

        PipelineStatus pipelineStatus = pipelineSteps.run(project, PipelineStatus.empty());

        if (config.sendEmailSummary()) {
            sendEmail(pipelineStatus);
        } else {
            log.info("Email disabled");
        }
    }

    private void sendEmail(PipelineStatus pipelineStatus) {
        log.info("Sending email");
        if (pipelineStatus.testsPassed && pipelineStatus.deploySuccessful) {
            emailer.send("Deployment completed successfully");
            return;
        }
        if (!pipelineStatus.testsPassed) {
            emailer.send("Tests failed");
            return;
        }
        emailer.send("Deployment failed");
    }


}
