// Smooth scroll for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Tab functionality for functions section
const tabButtons = document.querySelectorAll('.tab-btn');
const tabContents = document.querySelectorAll('.tab-content');

tabButtons.forEach(button => {
    button.addEventListener('click', () => {
        // Remove active class from all buttons and contents
        tabButtons.forEach(btn => btn.classList.remove('active'));
        tabContents.forEach(content => content.classList.remove('active'));

        // Add active class to clicked button
        button.classList.add('active');

        // Show corresponding content
        const tabId = button.getAttribute('data-tab');
        const targetContent = document.getElementById(tabId);
        if (targetContent) {
            targetContent.classList.add('active');
        }
    });
});

// Add animation on scroll
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, observerOptions);

// Observe all cards and sections
document.querySelectorAll('.feature-card, .example-card, .doc-card, .install-step').forEach(el => {
    el.style.opacity = '0';
    el.style.transform = 'translateY(20px)';
    el.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
    observer.observe(el);
});

// Add active state to navigation based on scroll position
window.addEventListener('scroll', () => {
    const sections = document.querySelectorAll('section[id]');
    const navLinks = document.querySelectorAll('.nav-links a[href^="#"]');

    let current = '';

    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.clientHeight;
        if (scrollY >= sectionTop - 200) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === `#${current}`) {
            link.classList.add('active');
        }
    });
});

// Copy code functionality (optional)
document.querySelectorAll('pre code').forEach(block => {
    block.addEventListener('click', () => {
        const code = block.textContent;
        navigator.clipboard.writeText(code).then(() => {
            // Visual feedback
            const originalBg = block.parentElement.style.background;
            block.parentElement.style.background = '#10b981';
            setTimeout(() => {
                block.parentElement.style.background = originalBg;
            }, 200);
        });
    });
});

// Mobile menu toggle (if needed)
const createMobileMenu = () => {
    const navbar = document.querySelector('.navbar .container');
    const navLinks = document.querySelector('.nav-links');

    if (window.innerWidth <= 768) {
        if (!document.querySelector('.mobile-menu-btn')) {
            const menuBtn = document.createElement('button');
            menuBtn.className = 'mobile-menu-btn';
            menuBtn.innerHTML = '☰';
            menuBtn.style.cssText = 'font-size: 2rem; background: none; border: none; cursor: pointer; color: var(--primary-color);';

            menuBtn.addEventListener('click', () => {
                navLinks.style.display = navLinks.style.display === 'flex' ? 'none' : 'flex';
                if (navLinks.style.display === 'flex') {
                    navLinks.style.flexDirection = 'column';
                    navLinks.style.position = 'absolute';
                    navLinks.style.top = '100%';
                    navLinks.style.right = '0';
                    navLinks.style.background = 'white';
                    navLinks.style.padding = '1rem';
                    navLinks.style.boxShadow = 'var(--shadow)';
                    navLinks.style.borderRadius = '8px';
                }
            });

            navbar.appendChild(menuBtn);
        }
    }
};

window.addEventListener('resize', createMobileMenu);
createMobileMenu();

// Add loading animation
window.addEventListener('load', () => {
    document.body.style.opacity = '0';
    setTimeout(() => {
        document.body.style.transition = 'opacity 0.5s';
        document.body.style.opacity = '1';
    }, 100);
});

// Console easter egg
console.log('%cAlgo-Compiler v1.0.0', 'font-size: 20px; font-weight: bold; color: #2563eb;');
console.log('%c40 fonctions intégrées • 5 types de données • Documentation complète', 'font-size: 12px; color: #10b981;');
console.log('%cDéveloppé avec Kotlin 1.9.20 et Java 21', 'font-size: 10px; color: #7c3aed;');

