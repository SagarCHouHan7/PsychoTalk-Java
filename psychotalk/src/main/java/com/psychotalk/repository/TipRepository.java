package com.psychotalk.repository;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TipRepository {

    public static final List<String> TIPS = List.of(
            "Take 5 minutes to write down three things you're grateful for each morning. Gratitude journaling is proven to improve mood and reduce stress within two weeks of consistent practice.",
            "Practice deep breathing for 3 minutes daily: inhale for 4 counts, hold for 4, exhale for 6 counts slowly. This activates your parasympathetic nervous system to calm anxiety instantly.",
            "Go for a 10-minute walk outdoors every day and notice five things around you mindfully. Mindful walking reduces rumination and boosts serotonin levels naturally without medication.",
            "Drink a full glass of water first thing every morning before coffee or tea. Proper hydration supports optimal brain function and prevents fatigue that worsens low moods significantly.",
            "Spend 5 minutes stretching your body gently from head to toe each morning. Physical movement releases endorphins, nature's antidepressants, to lift your spirits quickly and sustainably.",
            "Call or message a loved one daily to share something positive from your day. Strong social connections are scientifically linked to lower depression risk over time and better resilience.",
            "Set one small, achievable goal for today and celebrate completing it with a reward. Small wins build momentum and reinforce your sense of accomplishment and self-efficacy daily.",
            "Listen to your favorite upbeat song mindfully for 3 full minutes without distractions. Music therapy elevates dopamine levels and shifts your mindset away from negativity effectively.",
            "Declutter one small area in your living space for just 5 minutes each evening. A tidy environment reduces mental overload and promotes clearer, calmer thinking throughout your day.",
            "Practice saying 'no' politely to one non-essential task or request today. Setting healthy boundaries preserves your energy levels and prevents burnout effectively in the long run.",
            "Eat a handful of nuts or a square of dark chocolate as a mindful snack. These provide magnesium and antioxidants that stabilize mood and combat stress hormones naturally.",
            "End your day by listing one valuable lesson learned, no matter how small it seems. Reflective journaling fosters resilience and creates a positive outlook for tomorrow's challenges.",
            "Smile genuinely in the mirror for 30 seconds each morning, even if it feels forced. Facial feedback theory shows it triggers real happiness chemicals in your brain quickly.",
            "Write down one specific worry on paper, then schedule 5 minutes tomorrow to address it. Postponing rumination frees your mind for present-moment joy and productivity.",
            "Do 10 jumping jacks or energetic dance moves right now wherever you are. Quick bursts of cardio flood your system with feel-good neurotransmitters instantly and energize you.",
            "Name three things you can see, three you can hear, and three you can feel. Grounding techniques interrupt anxiety spirals and anchor you firmly in the present moment.",
            "Prepare tomorrow's clothes, lunch, and top three to-do items tonight before bed. Morning rituals reduce decision fatigue and set a positive, stress-free tone for your entire day.",
            "Hug a loved one or yourself firmly for a full 20 seconds today. Oxytocin release from physical touch lowers cortisol levels and builds lasting emotional security.",
            "Laugh out loud at a funny video, meme, or memory for at least 2 minutes. Laughter decreases stress hormones significantly and strengthens your immune function naturally.",
            "Stand tall with shoulders back and chest open for 2 full minutes, power pose style. This boosts confidence hormones and reduces feelings of powerlessness or overwhelm.",
            "Chew each bite of food slowly and savor flavors during at least one meal today. Mindful eating improves digestion, nutrient absorption, and creates calm moments amid busy schedules.",
            "Visualize your ideal calm evening in vivid detail for 2 minutes before bedtime. Mental rehearsal reduces nighttime worry and dramatically improves sleep quality and restoration.",
            "Compliment a stranger, coworker, or family member genuinely about something specific today. Acts of kindness trigger the helper's high, elevating your own mood significantly and creating positivity.",
            "Turn off all phone notifications for 30 minutes and focus deeply on one important task. Digital detoxes recharge your mental batteries and sharpen concentration for better results."
    );

    public static List<String> getTips(){
        return TIPS;
    }

}
