def call(String appName, String env) {
    echo "🚀 Deploying ${appName} to ${env}"
    sh """
        echo "Deploying ${appName}..."
        sleep 2
        echo "✅ Successfully deployed ${appName} to ${env}"
    """
}
