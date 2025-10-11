# 📚 Catálogo Completo - 50 Ejercicios de Algoritmos

## Índice por Categoría

1. [Arrays & Subarrays (01-10)](#arrays--subarrays-01-10)
2. [Strings & HashMap (11-20)](#strings--hashmap-11-20)
3. [Two Pointers & Sorting (21-30)](#two-pointers--sorting-21-30)
4. [Trees & Graphs (31-40)](#trees--graphs-31-40)
5. [Dynamic Programming (41-50)](#dynamic-programming-41-50)

---

## Arrays & Subarrays (01-10)

### 01. Subarray Sum Equals K
**Nivel:** Mid  
**Patrón:** Prefix Sum + HashMap  
**Complejidad:** O(n) tiempo, O(n) espacio

**Descripción:**  
Dado un array de enteros `nums` y un entero `k`, retornar el número de subarrays contiguos que suman exactamente `k`.

**Ejemplos:**
```
Input: nums = [1,1,1], k = 2
Output: 2
Explicación: [1,1] aparece dos veces

Input: nums = [1,2,3], k = 3
Output: 2
Explicación: [1,2] y [3]
```

**Hint:** Usa prefix sums. La clave es: `prefixSum[j] - prefixSum[i] = k` implica que hay un subarray de i+1 a j que suma k.

---

### 02. Maximum Subarray Sum (Kadane's Algorithm)
**Nivel:** Junior-Mid  
**Patrón:** Dynamic Programming / Kadane  
**Complejidad:** O(n) tiempo, O(1) espacio

**Descripción:**  
Encontrar el subarray contiguo que tiene la suma máxima y retornar esa suma.

**Ejemplos:**
```
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explicación: [4,-1,2,1] = 6

Input: nums = [5,4,-1,7,8]
Output: 23
```

**Hint:** Mantén un `currentSum` y un `maxSum`. Si `currentSum` se vuelve negativo, reinícialo a 0.

---

### 03. Product of Array Except Self
**Nivel:** Mid  
**Patrón:** Prefix & Suffix Products  
**Complejidad:** O(n) tiempo, O(1) espacio extra

**Descripción:**  
Retornar un array donde `output[i]` es el producto de todos los elementos de `nums` excepto `nums[i]`. **No puedes usar división**.

**Ejemplos:**
```
Input: nums = [1,2,3,4]
Output: [24,12,8,6]

Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
```

**Hint:** Primera pasada: calcula productos de izquierda. Segunda pasada: multiplica por productos de derecha.

---

### 04. Container With Most Water
**Nivel:** Mid  
**Patrón:** Two Pointers  
**Complejidad:** O(n) tiempo, O(1) espacio

**Descripción:**  
Dado un array `height` donde `height[i]` es la altura de la línea i, encontrar dos líneas que junto con el eje x formen un contenedor que contenga la mayor cantidad de agua.

**Ejemplos:**
```
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explicación: Líneas en índice 1 y 8, área = min(8,7) * 7 = 49
```

**Hint:** Comienza con punteros en ambos extremos. Mueve el puntero con la línea más corta.

---

### 05. Find All Duplicates in Array
**Nivel:** Mid  
**Patrón:** Array as HashMap  
**Complejidad:** O(n) tiempo, O(1) espacio extra

**Descripción:**  
Dado un array de enteros donde `1 ≤ nums[i] ≤ n` (n = tamaño del array), encontrar todos los elementos que aparecen dos veces.

**Ejemplos:**
```
Input: nums = [4,3,2,7,8,2,3,1]
Output: [2,3]
```

**Hint:** Usa el signo de `nums[abs(nums[i])-1]` para marcar visitados. Si ya es negativo, es duplicado.

---

### 06. Sliding Window Maximum
**Nivel:** Mid-Hard  
**Patrón:** Deque (Monotonic Queue)  
**Complejidad:** O(n) tiempo, O(k) espacio

**Descripción:**  
Dado un array y un entero `k`, retornar un array con el máximo de cada ventana de tamaño `k`.

**Ejemplos:**
```
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
```

**Hint:** Usa un deque que mantenga índices en orden decreciente de valores.

---

### 07. Trapping Rain Water
**Nivel:** Mid-Hard  
**Patrón:** Two Pointers  
**Complejidad:** O(n) tiempo, O(1) espacio

**Descripción:**  
Dado un array de alturas, calcular cuántas unidades de agua de lluvia pueden ser atrapadas después de llover.

**Ejemplos:**
```
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
```

**Hint:** Usa two pointers con `leftMax` y `rightMax`. El agua atrapada en posición i es `min(leftMax, rightMax) - height[i]`.

---

### 08. Maximum Product Subarray
**Nivel:** Mid  
**Patrón:** Dynamic Programming  
**Complejidad:** O(n) tiempo, O(1) espacio

**Descripción:**  
Encontrar el subarray contiguo que tiene el producto máximo.

**Ejemplos:**
```
Input: nums = [2,3,-2,4]
Output: 6
Explicación: [2,3] = 6

Input: nums = [-2,3,-4]
Output: 24
Explicación: Todo el array
```

**Hint:** Mantén `maxProduct` y `minProduct` porque un negativo puede convertir el mínimo en máximo.

---

### 09. Find Peak Element
**Nivel:** Mid  
**Patrón:** Binary Search  
**Complejidad:** O(log n) tiempo, O(1) espacio

**Descripción:**  
Un elemento pico es mayor que sus vecinos. Encontrar cualquier pico en el array.

**Ejemplos:**
```
Input: nums = [1,2,3,1]
Output: 2 (índice)

Input: nums = [1,2,1,3,5,6,4]
Output: 5 (o 1, ambos son picos)
```

**Hint:** Binary search. Si `nums[mid] < nums[mid+1]`, el pico está a la derecha.

---

### 10. Longest Consecutive Sequence
**Nivel:** Mid  
**Patrón:** HashSet  
**Complejidad:** O(n) tiempo, O(n) espacio

**Descripción:**  
Encontrar la longitud de la secuencia consecutiva más larga (los números deben ser consecutivos, pero no necesitan estar ordenados en el array).

**Ejemplos:**
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explicación: [1,2,3,4]

Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
Explicación: [0,1,2,3,4,5,6,7,8]
```

**Hint:** Pon todos en un HashSet. Para cada número, si es el inicio de una secuencia (num-1 no existe), cuenta cuántos consecutivos hay.

---

## Strings & HashMap (11-20)

### 11. Longest Substring Without Repeating Characters
**Nivel:** Mid  
**Patrón:** Sliding Window + HashSet  
**Complejidad:** O(n) tiempo, O(min(n,m)) espacio

**Descripción:**  
Encontrar la longitud del substring más largo sin caracteres repetidos.

**Ejemplos:**
```
Input: s = "abcabcbb"
Output: 3 ("abc")

Input: s = "pwwkew"
Output: 3 ("wke")
```

**Hint:** Usa sliding window con HashSet. Cuando encuentres duplicado, mueve el inicio.

---

### 12. Group Anagrams
**Nivel:** Mid  
**Patrón:** HashMap  
**Complejidad:** O(n * k log k) tiempo

**Descripción:**  
Agrupar strings que son anagramas.

**Ejemplos:**
```
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

**Hint:** Usa HashMap donde la key es el string ordenado.

---

### 13. Minimum Window Substring
**Nivel:** Hard  
**Patrón:** Sliding Window + HashMap  
**Complejidad:** O(n + m) tiempo

**Descripción:**  
Encontrar el substring mínimo de `s` que contiene todos los caracteres de `t`.

**Ejemplos:**
```
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
```

**Hint:** Expande ventana hasta tener todos los caracteres, luego contráela mientras sea válida.

---

### 14. Valid Parentheses
**Nivel:** Junior  
**Patrón:** Stack  
**Complejidad:** O(n) tiempo, O(n) espacio

**Descripción:**  
Determinar si los paréntesis/corchetes/llaves están balanceados.

**Ejemplos:**
```
Input: s = "()[]{}"
Output: true

Input: s = "([)]"
Output: false
```

**Hint:** Usa Stack. Push apertura, pop y verifica cierre.

---

### 15. Longest Palindromic Substring
**Nivel:** Mid  
**Patrón:** Expand Around Center  
**Complejidad:** O(n²) tiempo, O(1) espacio

**Descripción:**  
Encontrar el substring palíndromo más largo.

**Ejemplos:**
```
Input: s = "babad"
Output: "bab" (o "aba")
```

**Hint:** Para cada posición, expande alrededor del centro (considera centros pares e impares).

---

### 16. Find All Anagrams in a String
**Nivel:** Mid  
**Patrón:** Sliding Window  
**Complejidad:** O(n) tiempo

**Descripción:**  
Encontrar todos los índices de inicio de anagramas de `p` en `s`.

**Ejemplos:**
```
Input: s = "cbaebabacd", p = "abc"
Output: [0,6]
```

**Hint:** Sliding window de tamaño `p.length()` con array de frecuencias.

---

### 17. Encode and Decode Strings
**Nivel:** Mid  
**Patrón:** String Manipulation  
**Complejidad:** O(n) tiempo

**Descripción:**  
Diseñar algoritmo para codificar/decodificar lista de strings en un solo string.

**Ejemplos:**
```
Input: ["Hello","World"]
Encode: "5#Hello5#World"
Decode: ["Hello","World"]
```

**Hint:** Usa formato `length#string` para cada palabra.

---

### 18. Word Pattern
**Nivel:** Junior-Mid  
**Patrón:** Bidirectional HashMap  
**Complejidad:** O(n) tiempo

**Descripción:**  
Verificar si un string sigue un patrón dado (bijección).

**Ejemplos:**
```
Input: pattern = "abba", s = "dog cat cat dog"
Output: true

Input: pattern = "abba", s = "dog cat cat fish"
Output: false
```

**Hint:** Dos HashMaps: char→word y word→char.

---

### 19. Longest Repeating Character Replacement
**Nivel:** Mid  
**Patrón:** Sliding Window  
**Complejidad:** O(n) tiempo

**Descripción:**  
Puedes reemplazar `k` caracteres. Encontrar longitud del substring más largo con un solo carácter repetido.

**Ejemplos:**
```
Input: s = "AABABBA", k = 1
Output: 4
Explicación: Reemplazar una 'B' para "AABBBBA"
```

**Hint:** Sliding window. `windowSize - maxFreq <= k`.

---

### 20. Valid Anagram
**Nivel:** Junior  
**Patrón:** Frequency Counting  
**Complejidad:** O(n) tiempo, O(1) espacio

**Descripción:**  
Determinar si dos strings son anagramas.

**Ejemplos:**
```
Input: s = "anagram", t = "nagaram"
Output: true
```

**Hint:** Array de frecuencias o HashMap.

---

## Two Pointers & Sorting (21-30)

### 21. 3Sum
**Nivel:** Mid  
**Patrón:** Sort + Two Pointers  
**Complejidad:** O(n²) tiempo

**Descripción:**  
Encontrar todas las tripletas únicas que suman 0.

**Ejemplos:**
```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
```

**Hint:** Ordena el array. Para cada elemento, usa two pointers en el resto.

---

### 22. Sort Colors (Dutch National Flag)
**Nivel:** Mid  
**Patrón:** Three Pointers  
**Complejidad:** O(n) tiempo, O(1) espacio

**Descripción:**  
Ordenar array con solo 0s, 1s y 2s en one-pass.

**Ejemplos:**
```
Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```

**Hint:** Tres punteros: low, mid, high.

---

### 23. Remove Duplicates from Sorted Array II
**Nivel:** Mid  
**Patrón:** Two Pointers  
**Complejidad:** O(n) tiempo

**Descripción:**  
Cada elemento puede aparecer máximo dos veces.

**Ejemplos:**
```
Input: nums = [1,1,1,2,2,3]
Output: 5, nums = [1,1,2,2,3,_]
```

**Hint:** Compara `nums[i]` con `nums[write-2]`.

---

### 24. Merge Intervals
**Nivel:** Mid  
**Patrón:** Sorting + Merge  
**Complejidad:** O(n log n) tiempo

**Descripción:**  
Fusionar todos los intervalos superpuestos.

**Ejemplos:**
```
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
```

**Hint:** Ordena por inicio. Fusiona si `current.start <= previous.end`.

---

### 25. Insert Interval
**Nivel:** Mid  
**Patrón:** Merge  
**Complejidad:** O(n) tiempo

**Descripción:**  
Insertar un nuevo intervalo y fusionar si es necesario.

**Ejemplos:**
```
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
```

**Hint:** Tres fases: antes del nuevo, fusionar, después del nuevo.

---

### 26. Meeting Rooms II
**Nivel:** Mid  
**Patrón:** Sorting + Priority Queue  
**Complejidad:** O(n log n) tiempo

**Descripción:**  
Encontrar el mínimo número de salas de reuniones necesarias.

**Ejemplos:**
```
Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2
```

**Hint:** Ordena por inicio. Usa min-heap para finales de reuniones.

---

### 27. Non-overlapping Intervals
**Nivel:** Mid  
**Patrón:** Greedy + Sorting  
**Complejidad:** O(n log n) tiempo

**Descripción:**  
Mínimo número de intervalos a remover para que no se superpongan.

**Ejemplos:**
```
Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
Output: 1
Explicación: Remover [1,3]
```

**Hint:** Ordena por final. Greedy: siempre toma el que termina primero.

---

### 28. Container With Most Water II (3D)
**Nivel:** Hard  
**Patrón:** Priority Queue  
**Complejidad:** O(n² log n) tiempo

**Descripción:**  
Versión 2D del problema de contenedores.

**Hint:** Similar a trapping rain water pero en 2D, usa priority queue.

---

### 29. 4Sum
**Nivel:** Mid  
**Patrón:** Sort + Two Pointers (nested)  
**Complejidad:** O(n³) tiempo

**Descripción:**  
Encontrar todas las cuádruplas que suman target.

**Ejemplos:**
```
Input: nums = [1,0,-1,0,-2,2], target = 0
Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
```

**Hint:** Similar a 3Sum pero con un loop más externo.

---

### 30. Valid Triangle Number
**Nivel:** Mid  
**Patrón:** Sort + Two Pointers  
**Complejidad:** O(n²) tiempo

**Descripción:**  
Contar tripletas que pueden formar un triángulo válido.

**Ejemplos:**
```
Input: nums = [2,2,3,4]
Output: 3
Explicación: (2,3,4), (2,2,3), (2,2,4)
```

**Hint:** Ordena. Para cada lado mayor, cuenta pares con two pointers donde `a + b > c`.

---

## Trees & Graphs (31-40)

### 31. Binary Tree Level Order Traversal
**Nivel:** Mid  
**Patrón:** BFS (Breadth-First Search)  
**Complejidad:** O(n) tiempo, O(n) espacio

**Descripción:**  
Retornar el recorrido por niveles de un árbol binario.

**Ejemplos:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[9,20],[15,7]]
```

**Hint:** Usa una Queue. Procesa nivel por nivel.

---

### 32. Validate Binary Search Tree
**Nivel:** Mid  
**Patrón:** DFS con límites  
**Complejidad:** O(n) tiempo, O(h) espacio

**Descripción:**  
Verificar si un árbol binario es un BST válido.

**Ejemplos:**
```
Input: root = [2,1,3]
Output: true

Input: root = [5,1,4,null,null,3,6]
Output: false
```

**Hint:** DFS con rango `(min, max)`. Left debe estar en `(min, node.val)`, right en `(node.val, max)`.

---

### 33. Lowest Common Ancestor of BST
**Nivel:** Mid  
**Patrón:** BST Properties  
**Complejidad:** O(h) tiempo, O(1) espacio

**Descripción:**  
Encontrar el ancestro común más bajo de dos nodos en un BST.

**Ejemplos:**
```
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
```

**Hint:** Si ambos nodos están a la izquierda/derecha, ve en esa dirección. Si están en lados opuestos, el nodo actual es el LCA.

---

### 34. Serialize and Deserialize Binary Tree
**Nivel:** Hard  
**Patrón:** DFS/BFS + String Encoding  
**Complejidad:** O(n) tiempo

**Descripción:**  
Diseñar algoritmo para serializar y deserializar un árbol binario.

**Ejemplos:**
```
Input: root = [1,2,3,null,null,4,5]
Serialize: "1,2,null,null,3,4,null,null,5,null,null"
Deserialize: árbol original
```

**Hint:** Preorder con marcadores null. Usa Queue para deserializar.

---

### 35. Word Ladder
**Nivel:** Hard  
**Patrón:** BFS en grafo implícito  
**Complejidad:** O(n * m²) tiempo donde n = wordList size, m = word length

**Descripción:**  
Encontrar la longitud del camino más corto transformando `beginWord` en `endWord` cambiando una letra a la vez.

**Ejemplos:**
```
Input: beginWord = "hit", endWord = "cog", 
       wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explicación: "hit" -> "hot" -> "dot" -> "dog" -> "cog"
```

**Hint:** BFS. Cada palabra es un nodo. Conecta palabras que difieren en una letra.

---

### 36. Course Schedule (Topological Sort)
**Nivel:** Mid  
**Patrón:** DFS + Cycle Detection  
**Complejidad:** O(V + E) tiempo

**Descripción:**  
Determinar si puedes terminar todos los cursos dados los prerequisitos (detectar ciclos).

**Ejemplos:**
```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explicación: Tomar curso 0 primero, luego 1

Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explicación: Hay un ciclo
```

**Hint:** Construye grafo dirigido. Usa DFS con estados: no visitado, visitando, visitado. Si encuentras nodo "visitando", hay ciclo.

---

### 37. Number of Islands
**Nivel:** Mid  
**Patrón:** DFS/BFS en matriz  
**Complejidad:** O(m * n) tiempo

**Descripción:**  
Contar el número de islas en una matriz de '1' (tierra) y '0' (agua).

**Ejemplos:**
```
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
```

**Hint:** DFS/BFS desde cada '1' no visitado, marca toda la isla conectada.

---

### 38. Clone Graph
**Nivel:** Mid  
**Patrón:** DFS/BFS + HashMap  
**Complejidad:** O(n) tiempo, O(n) espacio

**Descripción:**  
Clonar un grafo no dirigido (deep copy).

**Ejemplos:**
```
Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
Output: copia profunda del grafo
```

**Hint:** Usa HashMap para mapear nodo original → nodo clonado. DFS/BFS para recorrer.

---

### 39. Pacific Atlantic Water Flow
**Nivel:** Mid  
**Patrón:** DFS desde bordes  
**Complejidad:** O(m * n) tiempo

**Descripción:**  
Encontrar celdas desde las cuales el agua puede fluir tanto al océano Pacífico como al Atlántico.

**Ejemplos:**
```
Input: heights = [
  [1,2,2,3,5],
  [3,2,3,4,4],
  [2,4,5,3,1],
  [6,7,1,4,5],
  [5,1,1,2,4]
]
Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
```

**Hint:** DFS desde bordes del Pacífico y Atlántico hacia arriba. Las celdas alcanzadas por ambos son la respuesta.

---

### 40. Graph Valid Tree
**Nivel:** Mid  
**Patrón:** Union-Find o DFS  
**Complejidad:** O(n) tiempo

**Descripción:**  
Verificar si un grafo no dirigido es un árbol válido (conectado y sin ciclos).

**Ejemplos:**
```
Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
Output: true

Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
Output: false
Explicación: Hay un ciclo [1,2,3]
```

**Hint:** Un árbol tiene exactamente n-1 aristas y es conectado. Usa Union-Find o DFS para verificar.

---

## Dynamic Programming & Advanced (41-50)

### 41. Coin Change
**Nivel:** Mid  
**Patrón:** DP Bottom-Up  
**Complejidad:** O(n * amount) tiempo

**Descripción:**  
Dado un array de monedas y un monto, encontrar el número mínimo de monedas para formar ese monto.

**Ejemplos:**
```
Input: coins = [1,2,5], amount = 11
Output: 3
Explicación: 11 = 5 + 5 + 1

Input: coins = [2], amount = 3
Output: -1
```

**Hint:** `dp[i]` = mínimo de monedas para monto i. `dp[i] = min(dp[i], dp[i - coin] + 1)` para cada moneda.

---

### 42. House Robber
**Nivel:** Mid  
**Patrón:** DP con decisiones  
**Complejidad:** O(n) tiempo, O(1) espacio

**Descripción:**  
No puedes robar dos casas adyacentes. Maximizar dinero robado.

**Ejemplos:**
```
Input: nums = [1,2,3,1]
Output: 4
Explicación: Robar casa 1 (1) y 3 (3) = 4

Input: nums = [2,7,9,3,1]
Output: 12
Explicación: Robar 2, 9, 1 = 12
```

**Hint:** `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`. Puedes optimizar a O(1) espacio.

---

### 43. Longest Increasing Subsequence
**Nivel:** Mid  
**Patrón:** DP + Binary Search  
**Complejidad:** O(n log n) tiempo

**Descripción:**  
Encontrar la longitud de la subsecuencia creciente más larga.

**Ejemplos:**
```
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explicación: [2,3,7,101] o [2,3,7,18]

Input: nums = [0,1,0,3,2,3]
Output: 4
```

**Hint:** Solución O(n²) DP: `dp[i]` = LIS terminando en i. Solución O(n log n): mantén array de "tails" más pequeños para cada longitud, usa binary search.

---

### 44. Unique Paths
**Nivel:** Mid  
**Patrón:** DP 2D  
**Complejidad:** O(m * n) tiempo

**Descripción:**  
Robot en esquina superior izquierda de m×n grid. Solo puede moverse abajo o derecha. ¿Cuántos caminos únicos a la esquina inferior derecha?

**Ejemplos:**
```
Input: m = 3, n = 7
Output: 28

Input: m = 3, n = 2
Output: 3
```

**Hint:** `dp[i][j] = dp[i-1][j] + dp[i][j-1]`. Puedes optimizar a O(n) espacio.

---

### 45. Jump Game
**Nivel:** Mid  
**Patrón:** Greedy o DP  
**Complejidad:** O(n) tiempo

**Descripción:**  
Cada elemento es el máximo salto desde esa posición. ¿Puedes alcanzar el último índice?

**Ejemplos:**
```
Input: nums = [2,3,1,1,4]
Output: true
Explicación: Salta 1 paso de índice 0 a 1, luego 3 pasos al último

Input: nums = [3,2,1,0,4]
Output: false
```

**Hint:** Greedy: mantén `maxReach`. Si `i > maxReach`, retorna false.

---

### 46. Decode Ways
**Nivel:** Mid  
**Patrón:** DP en strings  
**Complejidad:** O(n) tiempo

**Descripción:**  
Un mensaje codificado donde 'A'=1, 'B'=2, ..., 'Z'=26. ¿Cuántas formas de decodificarlo?

**Ejemplos:**
```
Input: s = "12"
Output: 2
Explicación: "AB" (1 2) o "L" (12)

Input: s = "226"
Output: 3
Explicación: "BZ" (2 26), "VF" (22 6), "BBF" (2 2 6)
```

**Hint:** `dp[i]` = formas de decodificar hasta i. Considera uno o dos dígitos.

---

### 47. Word Break
**Nivel:** Mid  
**Patrón:** DP + HashSet  
**Complejidad:** O(n²) tiempo

**Descripción:**  
Determinar si un string puede ser segmentado en palabras del diccionario.

**Ejemplos:**
```
Input: s = "leetcode", wordDict = ["leet","code"]
Output: true
Explicación: "leet code"

Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: false
```

**Hint:** `dp[i]` = true si s[0:i] puede ser segmentado. Chequea todos los substrings s[j:i].

---

### 48. Partition Equal Subset Sum (0/1 Knapsack)
**Nivel:** Mid  
**Patrón:** DP - 0/1 Knapsack  
**Complejidad:** O(n * sum) tiempo

**Descripción:**  
Determinar si puedes particionar el array en dos subsets con suma igual.

**Ejemplos:**
```
Input: nums = [1,5,11,5]
Output: true
Explicación: [1,5,5] y [11]

Input: nums = [1,2,3,5]
Output: false
```

**Hint:** Si la suma total es impar, retorna false. Problema se reduce a: ¿existe subset que sume sum/2?

---

### 49. Palindromic Substrings
**Nivel:** Mid  
**Patrón:** DP o Expand Center  
**Complejidad:** O(n²) tiempo

**Descripción:**  
Contar cuántos substrings palindrómicos hay.

**Ejemplos:**
```
Input: s = "abc"
Output: 3
Explicación: "a", "b", "c"

Input: s = "aaa"
Output: 6
Explicación: "a", "a", "a", "aa", "aa", "aaa"
```

**Hint:** Expand around center para cada posición (centro impar y par).

---

### 50. Regular Expression Matching
**Nivel:** Hard  
**Patrón:** DP 2D complejo  
**Complejidad:** O(n * m) tiempo

**Descripción:**  
Implementar regex matching con '.' (cualquier carácter) y '*' (0 o más del anterior).

**Ejemplos:**
```
Input: s = "aa", p = "a"
Output: false

Input: s = "aa", p = "a*"
Output: true

Input: s = "ab", p = ".*"
Output: true
```

**Hint:** DP 2D. `dp[i][j]` = true si s[0:i] matches p[0:j]. Casos especiales para '*'.

---

## 📊 Resumen de Complejidades

### Por Complejidad Temporal
- **O(1)**: Ninguno en esta lista (todos requieren al menos recorrer)
- **O(log n)**: 09, 32
- **O(n)**: 01, 02, 04, 05, 07, 10, 11, 13, 14, 16, 20, 31, 37, 38, 42, 45, 46, 47
- **O(n log n)**: 24, 26, 43
- **O(n²)**: 12, 15, 21, 22, 29, 30, 48, 49
- **O(n³)**: 29
- **Especiales**: 06 O(n), 35 O(n*m²), 36 O(V+E)

### Por Patrón Principal
- **Sliding Window**: 06, 11, 13, 16, 19
- **Two Pointers**: 04, 07, 21, 22, 23, 27, 30
- **HashMap/HashSet**: 01, 05, 10, 12, 18, 20, 38, 47
- **Stack/Queue**: 14, 31
- **DFS**: 32, 33, 36, 37, 39, 40
- **BFS**: 31, 35, 38
- **DP**: 02, 08, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50
- **Binary Search**: 09, 43
- **Union-Find**: 40
- **Greedy**: 27, 45

---

## 🎯 Ruta de Aprendizaje Sugerida

### Fase 1: Fundamentos (Semana 1-2)
Ejercicios: 01-10, 11, 14, 20  
**Objetivo:** Dominar arrays básicos, sliding window simple, stack

### Fase 2: Intermedio (Semana 3-4)
Ejercicios: 12-19, 21-25  
**Objetivo:** Sliding window avanzado, two pointers, sorting

### Fase 3: Árboles y Grafos (Semana 5)
Ejercicios: 31-40  
**Objetivo:** BFS, DFS, topological sort

### Fase 4: DP y Problemas Complejos (Semana 6-7)
Ejercicios: 41-50, 06, 07, 13, 26-30  
**Objetivo:** Programación dinámica, problemas difíciles

---

**¡Buena suerte con tu práctica! 🚀**

*Este catálogo es tu referencia completa para los 50 ejercicios.*