let currentVideoUrl = "";

function showProfileAndVideoSection() {
  document.getElementById("videoSection").style.display = "block";
  document.getElementById("profileSection").style.display = "block";
}

function playVideo() {
  const videoPlayer = document.getElementById("videoPlayer");
  videoPlayer.style.display = "block";
  videoPlayer.src = currentVideoUrl;
  videoPlayer.load();
  videoPlayer.play();
}

function handleUrl() {
  const urlInput = document.getElementById("videoUrlInput");
  const status = document.getElementById("status");

  const videoUrl = urlInput.value.trim();
  if (!videoUrl) {
    alert("Please enter a valid video URL.");
    return;
  }

  currentVideoUrl = videoUrl;
  status.textContent = "URL submitted successfully.";
  showProfileAndVideoSection();
}

async function handleUpload() {
  const fileInput = document.getElementById('videoInput');
  const status = document.getElementById('status');

  if (fileInput.files.length === 0) {
    alert("Please choose a video file.");
    return;
  }

  const file = fileInput.files[0];
  const formData = new FormData();
  formData.append("file", file);

  status.textContent = "Uploading...";

  try {
    const response = await fetch("http://localhost:8080/s3/upload", {
      method: "POST",
      body: formData
    });

    if (!response.ok) {
      throw new Error("Upload failed.");
    }

    const result = await response.json();
    currentVideoUrl = result.secureUrl || result.url || result.data;

    status.textContent = "Upload successful!";
    showProfileAndVideoSection();
  } catch (error) {
    status.textContent = "Error: " + error.message;
    console.error("Upload error:", error);
  }
}
