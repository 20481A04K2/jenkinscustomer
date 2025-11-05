def call() {
    echo "🚀 Building application..."
    sh '''
        echo "Simulating build process..."
        mkdir -p build
        echo "Build successful" > build/output.txt
    '''
    echo "✅ Build completed successfully."
}
