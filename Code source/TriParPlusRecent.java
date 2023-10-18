public class TriParPlusRecent extends Tri{

    @Override
    public BaseDeRegles trier(BaseDeRegles br, BaseDeFaits bf) {
        br.trierPlusRecent(bf);
        return br;
    }
}
