def call(String appName) {
    echo "🏗️  Building application: ${appName}"
    sh """
        echo "Installing dependencies for ${appName}"
        sleep 2
        echo "Running build for ${appName}"
        sleep 2
        echo "✅ Build completed for ${appName}"
    """
}
