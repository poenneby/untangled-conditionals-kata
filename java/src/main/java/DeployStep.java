import dependencies.Logger;
import dependencies.Project;

class DeployStep extends PipelineStep {

    DeployStep(PipelineStep nextStep, Logger log) {
        super(nextStep, log);
    }

    PipelineStatus run(Project project, PipelineStatus pipelineStatus) {
        if ("success".equals(project.deploy())) {
            log.info("Deployment successful");
            return pipelineStatus.withDeploymentSuccessful();
        }
        log.error("Deployment failed");
        return pipelineStatus.withDeploymentFailed();
    }
}