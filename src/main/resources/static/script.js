document.addEventListener("DOMContentLoaded", () => {
  const pointSearch = document.getElementById("pointSearch");
  const pointSelect = document.getElementById("pointSelect");
  const addPointBtn = document.getElementById("addPointBtn");
  const clearBtn = document.getElementById("clearBtn");
  const calcBtn = document.getElementById("calcBtn");
  const pathList = document.getElementById("pathList");
  const resultPanel = document.getElementById("resultPanel");
  const resultContent = document.getElementById("resultContent");

  const allPoints = ["A", "B", "C", "D"];

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
  });

  // Clear path
  clearBtn.addEventListener("click", () => {
    pathList.innerHTML = "";
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
