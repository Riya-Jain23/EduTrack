package Other;

public class Feedback<T> {
    private T feedbackContent;

    public Feedback(T feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
    public T getFeedbackContent() {
        return feedbackContent;
    }
    public void setFeedbackContent(T feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
    @Override
    public String toString() {
        return feedbackContent.toString();
    }
}
