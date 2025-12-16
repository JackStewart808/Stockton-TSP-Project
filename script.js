const ALL_POINTS = [
  "Albany", "Boston", "Buffalo", "Hartford", "New York",
  "Newark", "Philadelphia", "Pittsburgh", "Providence",
  "Rochester", "Syracuse", "Trenton"
];
// This just needs to be filled up with the actual nodes list, from whichever way we have it in the other folders
const pointSearch = document.getElementById("pointSearch");
const pointSelect = document.getElementById("pointSelect");
const addPointBtn = document.getElementById("addPointBtn");
const clearBtn = document.getElementById("clearBtn");
const pathList = document.getElementById("pathList");

let currentPath = [];

// Populate dropdown
function renderOptions(filterText = "") {
  const q = filterText.trim().toLowerCase();
  const filtered = ALL_POINTS.filter(p => p.toLowerCase().includes(q));
  const calcBtn = document.getElementById("calcBtn");
  const resultPanel = document.getElementById("resultPanel");

  pointSelect.innerHTML = "";
  filtered.forEach(name => {
    const opt = document.createElement("option");
    opt.value = name;
    opt.textContent = name;
    pointSelect.appendChild(opt);
  });

  
  if (pointSelect.options.length > 0) pointSelect.selectedIndex = 0;
}

function renderPath() {
  pathList.innerHTML = "";
  currentPath.forEach(p => {
    const li = document.createElement("li");
    li.textContent = p;
    pathList.appendChild(li);
  });
}


pointSearch.addEventListener("input", (e) => {
  renderOptions(e.target.value);
});

addPointBtn.addEventListener("click", () => {
  const selected = pointSelect.value;
  if (!selected) return;

  // prevents duplicates
  if (currentPath.includes(selected)) return;

  currentPath.push(selected);
  renderPath();
});

clearBtn.addEventListener("click", () => {
  currentPath = [];
  renderPath();
});

calcBtn.addEventListener("click", () => {
  resultPanel.classList.remove("hidden");
});

// Initial load
renderOptions("");
renderPath();
