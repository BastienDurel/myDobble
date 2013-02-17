math.randomseed(os.time())
function dobble(prefixe,p)
   -- prefixe : préfixe des fichiers d'image
   --           (symboles présents sur les cartes) pdf
   --           les noms fichiers doivent être de la forme prefixe001.pdf etc.
   -- p       : nombre premier (génération de 1+p+p² cartes à partir
   --           du même nombre de symboles
   -- on va générer un tableau où chaque case correspond
   -- à une carte et contient la liste des numéros de symbole à mettre
   local cartes={};
   -- première carte
   cartes[0]={};
   for i=0,p do
      cartes[0][i]=i;
   end
   printcard(cartes[0],p,prefixe);
   -- p+p*p suivantes
   for i=1,p do
      -- p premières
      cartes[i]={};
      for j=0,p do
         if j==0 then
            cartes[i][j]=0;
         else
            -- on place les symboles aléatoirement
            table.insert(cartes[i],intrand(j),i*p+j);
         end
      end
     printcard(cartes[i],p,prefixe);
      -- p*p suivantes
      for j=1,p do
         cartes[p+(i-1)*p+j]={};
         for k=0,p do
            if k==0 then
               cartes[p+(i-1)*p+j][k]=i;
            else
               -- on place les symboles aléatoirement
               --table.insert(cartes[p + (i - 1) * p + j], intrand(k), (k) * p + ((k * i + j - 1) % p) + 1);
            end
         end
        printcard(cartes[p+(i-1)*p+j],p,prefixe);
      end
   end
   return cartes;
end
 
function printcard(carte,p,prefixe)
	s = "";
	for i=0,p do
		s = s..i..", ";
	end
	print (s);
end
 
function printcard2(carte,p,prefixe)
   -- prend une carte en entrée (tableau de p+1 éléments)
   -- et génère le code tex d'une page à partir des images
   -- nommées suivant le prefixe
   tex.sprint("\\newpage\\begin{tikzpicture}[remember picture, overlay]\\coordinate(Or) at (current page.north west);");
   -- cadre de la carte
   tex.sprint("\\draw[rounded corners=0.5cm, line width=.2cm] ($(Or)+(0.1,-0.1)$) rectangle ($(Or)+(7.9,-7.9)$);");
   -- entier aléatoire pour l'élément au centre
   local has=intrand(p+1);
   local compt=0;
   for i=0,p do
      -- l'image au contre
      if i== has then
         tex.sprint("\\node[scale="..scalerand(0.7,1.3)..",rotate="..intrand(360).."] at ($(Or)+(4,-4)$) {\\includegraphics[width=1cm]{"..prefixe..string.format("%02d",carte[i]+1).."}};");
      else
         -- les autres tout le tour, taille aléatoire, rayon variant, rotation aléatoire
         tex.sprint("\\node[scale="..scalerand(0.7,1.3)..",rotate="..intrand(360).."] at ($("..(360/(p)*compt)..":"..scalerand(2.3,3)..")+(Or)+(4,-4)$) {\\includegraphics[width=1cm]{"..prefixe..string.format("%02d",carte[i]+1).."}};");
         compt=compt+1;
      end
   end
   tex.sprint("\\end{tikzpicture}");
end
 
 
function intrand(p) -- retourne un entier entre 0 et p-1
   return math.floor(p*math.random());
end
 
function scalerand(a,b) -- retourne un nombre aléatoire entre a et b
   return a+(b-a)*math.random();
end

dobble("", 3);

