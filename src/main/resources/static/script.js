document.addEventListener("DOMContentLoaded", () => {
  const pointSearch = document.getElementById("pointSearch");
  const pointSelect = document.getElementById("pointSelect");
  const addPointBtn = document.getElementById("addPointBtn");
  const clearBtn = document.getElementById("clearBtn");
  const calcBtn = document.getElementById("calcBtn");
  const pathList = document.getElementById("pathList");
  const resultPanel = document.getElementById("resultPanel");
  const resultContent = document.getElementById("resultContent");
  const mapOverlay = document.getElementById("mapOverlay");
  const lineCanvas = document.getElementById("lineCanvas");
  const ctx = lineCanvas.getContext("2d");

  const allPoints = [
    "A-Wing(00s)", "A-Wing(100s)", 
    "B-Wing(00s)", "B-Wing(100s)", 
    "C-Wing(00s)", "C-Wing(100s)",
    "D-Wing(00s)", "D-Wing(100s)",
    "F-Wing(100s)", "F-Wing(200s)",
    "G-Wing(100s)", "G-Wing(200s)",
    "H-Wing(100s)", "H-Wing(200s)",
    "I-Wing(100s)", "I-Wing(200s)",
    "J-Wing(100s)", "J-Wing(200s)",
    "K-Wing(100s)", "K-Wing(200s)",
    "N-Wing(100s)",
    "Campus_Center"
  ];
  
  lineCanvas.width = window.innerWidth;
  lineCanvas.height = window.innerHeight;

  const POINT_COORDS = {
    "A-Wing(00s)": {x: 1200, y: 150}, "A-Wing(100s)": {x: 1190, y: 140}, 
    "B-Wing(00s)": {x: 1130, y: 150}, "B-Wing(100s)": {x: 1120, y: 140}, 
    "C-Wing(00s)": {x: 1090, y: 145}, "C-Wing(100s)": {x: 1070, y: 130},
    "D-Wing(00s)": {x: 1000, y: 145}, "D-Wing(100s)": {x: 980, y: 130},
    "F-Wing(100s)": {x: 890, y: 230}, "F-Wing(200s)": {x: 860, y: 280},
    "G-Wing(100s)": {x: 780, y: 320}, "G-Wing(200s)": {x: 760, y: 350},
    "H-Wing(100s)": {x: 730, y: 390}, "H-Wing(200s)": {x: 710, y: 410},
    "I-Wing(100s)": {x: 670, y: 440}, "I-Wing(200s)": {x: 650, y: 450},
    "J-Wing(100s)": {x: 630, y: 480}, "J-Wing(200s)": {x: 610, y: 500},
    "K-Wing(100s)": {x: 530, y: 550}, "K-Wing(200s)": {x: 500, y: 550},
    "N-Wing(100s)": {x: 300, y: 560},
    "Campus_Center": {x: 960, y: 540},
  }

  // Populate the select box
  function updateSelect(filter = "") {
    pointSelect.innerHTML = "";
    const filtered = allPoints.filter(p =>
      p.toLowerCase().includes(filter.toLowerCase())
    );
    filtered.forEach(point => {
      const option = document.createElement("option");
      option.value = point;
      option.textContent = point;
      pointSelect.appendChild(option);
    });
  }

  updateSelect();

  // Filter points as user types
  pointSearch.addEventListener("input", () => {
    updateSelect(pointSearch.value);
  });

  // Add point to path
  addPointBtn.addEventListener("click", () => {
    const selected = pointSelect.value;
    if (!selected) return;
    // Prevent duplicates
    if ([...pathList.children].some(li => li.textContent === selected)) return;

    const li = document.createElement("li");
    li.textContent = selected;
    pathList.appendChild(li);

    placeDot(selected);
    drawLines();
  });

  function placeDot(pointId) {
    const pos = POINT_COORDS[pointId];
    if (!pos) return; //Coords dont exist

    //Dont duplicate
    if (mapOverlay.querySelector(`.map-dot[data-id="${pointId}"]`)) return;
    const dot = document.createElement("div");
    dot.className = "map-dot";
    dot.dataset.id = pointId;
    dot.style.left = pos.x + "px";
    dot.style.top = pos.y + "px";
    mapOverlay.appendChild(dot);
  }

  function clearDots() {
    mapOverlay.innerHTML = "";
  }

  function drawLines() {
    ctx.clearRect(0, 0, lineCanvas.lineCanvas.width, lineCanvas.height);

    const points = Array.from(pathList.children)
      .map(li => POINT_COORDS[li.textContent])
      .filter(Boolean);

    if (points.length < 2) return;

    ctx.beginPath();
    ctx.moveTo(points[0].x, points[0].y);

    for (let i = 1; i < points.length; i++) {
      ctx.lineTo(points[i].x, points[i].y);
    }

    ctx.stroke();
  }

  // Clear path
  clearBtn.addEventListener("click", () => {
    pathList.innerHTML = "";
    clearDots();
    ctx.clearRect(0, 0, lineCanvas.width, lineCanvas,height);
    resultContent.textContent = "(nothing)";
    resultPanel.classList.add("hidden");
  });

  // Calculate TSP path
  calcBtn.addEventListener("click", async () => {
    const points = Array.from(pathList.children).map(li => li.textContent);

    if (points.length === 0) {
      alert("No points selected!");
      return;
    }

    try {
      const response = await fetch("/tsp", {
        method: "POST",
        headers: { "Content-Type": "text/plain" },
        body: points.join(",")
      });

      if (!response.ok) throw new Error("Network error");

      const result = await response.text();

      // Display result
      resultContent.textContent = result;
      resultPanel.classList.remove("hidden");
    } catch (err) {
      console.error(err);
      alert("Failed to calculate path");
    }
  });
});
